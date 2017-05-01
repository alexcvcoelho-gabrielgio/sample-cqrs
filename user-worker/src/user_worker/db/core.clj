(ns user-worker.db.core
  (:require [monger.core :as mg]
            [monger.collection :as mc]
            [datomic.api :as d]))

(def dat-conn (d/connect "datomic:sql://?jdbc:mysql://localhost:3306/datomic?user=datomic&password=datomic"))

(defn create-schema []
  (let [schema [{:db/ident              :user/id
                 :db/valueType          :db.type/string
                 :db/cardinality        :db.cardinality/one
                 :db.install/_attribute :db.part/db}
                {:db/ident              :user/name
                 :db/valueType          :db.type/string
                 :db/cardinality        :db.cardinality/one
                 :db.install/_attribute :db.part/db}
                {:db/ident              :user/email
                 :db/valueType          :db.type/string
                 :db/cardinality        :db.cardinality/one
                 :db.install/_attribute :db.part/db}]]
    @(d/transact dat-conn schema)))

(defn entity [conn id]
  (d/entity (d/db conn) id))

(defn touch [conn results]
  (let [e (partial entity conn)]
    (map #(-> % first e d/touch) results)))

(defn add-user [conn {:keys [id name email]}]
  @(d/transact conn [{:db/id      (.toString id)
                      :user/name  name
                      :user/email email}]))

(defn find-user [conn id]
  (let [user (d/q '[:find ?e :in $ ?id
                    :where [?e :user/id ?id]]
                  (d/db conn) id)]
    (touch conn user)))

(defn log-done [trackId]
  (let [connmc (mg/connect)
        db (mg/get-db connmc "main")]
    (mc/insert db "log" {:trackId trackId :status :done})
    (mg/disconnect connmc)))


(defn create-user [user]
  (let [connmc (mg/connect)
        db (mg/get-db connmc "main")]
    (add-user dat-conn (assoc user :id (:_id (mc/insert-and-return db "user" user))))
    (mg/disconnect connmc)))