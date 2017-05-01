(ns user
  (:require [mount.core :as mount]
            user-query.core))

(defn start []
  (mount/start-without #'user-query.core/repl-server))

(defn stop []
  (mount/stop-except #'user-query.core/repl-server))

(defn restart []
  (stop)
  (start))


