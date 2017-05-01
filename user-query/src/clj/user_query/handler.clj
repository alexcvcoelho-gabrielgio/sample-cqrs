(ns user-query.handler
  (:require [compojure.core :refer [routes wrap-routes]]
            [user-query.layout :refer [error-page]]
            [user-query.routes.home :refer [home-routes]]
            [user-query.routes.services :refer [service-routes]]
            [compojure.route :as route]
            [user-query.env :refer [defaults]]
            [mount.core :as mount]
            [user-query.middleware :as middleware]))

(mount/defstate init-app
                :start ((or (:init defaults) identity))
                :stop  ((or (:stop defaults) identity)))

(def app-routes
  (routes
    (-> #'home-routes
        (wrap-routes middleware/wrap-csrf)
        (wrap-routes middleware/wrap-formats))
    #'service-routes
    (route/not-found
      (:body
        (error-page {:status 404
                     :title "page not found"})))))


(defn app [] (middleware/wrap-base #'app-routes))
