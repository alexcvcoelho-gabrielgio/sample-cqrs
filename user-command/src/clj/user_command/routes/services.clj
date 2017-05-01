(ns user-command.routes.services
  (:require [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [schema.core :as s]
            [user-command.redis.core :as redis :refer [push]]))

(defapi service-routes
  {:swagger {:ui   "/swagger-ui"
             :spec "/swagger.json"
             :data {:info {:version     "1.0.0"
                           :title       "User API"
                           :description "User Command Domain"}}}}

  (context "/api" []
    :tags ["user"]

    (POST "/user" []
      :return {:trackId String}
      :body-params [name :- String
                    email :- String
                    country :- String]
      :summary "Create a new user"
      (ok (let [uuid (str (java.util.UUID/randomUUID))]
            (push "user" {:trackId uuid :command "create_user_command" :name name :email email :country country})
            {:trackId uuid})))

    (PUT "/user" []
      :return {:trackId String}
      :body-params [uid :- String
                    {email :- String nil}
                    {name :- String nil}
                    {country :- String nil}]
      :summary "Update user information"
      (let [uuid (str (java.util.UUID/randomUUID))]
        (push "user" {:trackId uuid :command "update_user_command" :uid uid :name name :email email :country country})
        (ok {:trackId uuid})))

    (DELETE "/user" []
      :return {:trackId String}
      :body-params [uid :- String]
      :summary "Mark user as inactive"
      (let [uuid (str (java.util.UUID/randomUUID))]
        (push "user" {:trackId uuid :command "delete_user_command" :uid uid})
        (ok {:trackId uuid})))))
