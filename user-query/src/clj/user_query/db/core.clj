(ns user-query.db.core
  (:require [monger.core :as mg]
            [monger.collection :as mc]
            [monger.operators :refer :all]
            [mount.core :refer [defstate]]
            [user-query.config :refer [env]]))

(defstate db*
          :start (-> env :database-url mg/connect-via-uri)
          :stop (-> db* :conn mg/disconnect))

(defstate db
          :start (:db db*))

(defn create-user [user]
  (mc/insert db "users" user))

(defn update-user [id first-name last-name email]
  (mc/update db "users" {:_id id}
             {$set {:first_name first-name
                    :last_name  last-name
                    :email      email}}))

(defn get-user [id]
  (mc/find-one-as-map db "user" {:_id id}))

(defn get-users []
  (mc/find-maps db "user" {} [:email :name]))

(defn get-user-filter [filter]
  (mc/find-maps db "user" (select-keys filter (for [[k m] filter :when (not= m nil)] k)) [:email :name]))