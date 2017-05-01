(ns user-query.routes.services
  (:require [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [schema.core :as s]
            [user-query.db.core :as db :refer :all]
            [monger.conversion :refer [from-db-object]]))

(defapi service-routes
  {:swagger {:ui   "/swagger-ui"
             :spec "/swagger.json"
             :data {:info {:version     "1.0.0"
                           :title       "Sample API"
                           :description "Sample Services"}}}}

  (context "/api" []
    :tags ["user"]

    (GET "/user" []
      :return [{:email String, :name String, :id String}]
      :summary "Get all user."
      (let [users (get-users)]
        (ok (map (fn [x] {:name (:name x) :email (:email x) :id (.toString (:_id x))}) users))))

    (POST "/user/filter" []
      :return [{:email String, :name String, :id String}]
      :body-params [{id :- String nil}
                    {email :- String nil}
                    {name :- String nil}]
      :summary "Get user by email, name or id."
      (let [users (get-user-filter {:id id :email email :name name})]
        (ok (map (fn [x] {:name (:name x) :email (:email x) :id (.toString (:_id x))}) users))))))
