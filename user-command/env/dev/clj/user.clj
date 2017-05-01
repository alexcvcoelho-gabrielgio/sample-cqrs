(ns user
  (:require [mount.core :as mount]
            user-command.core))

(defn start []
  (mount/start-without #'user-command.core/repl-server))

(defn stop []
  (mount/stop-except #'user-command.core/repl-server))

(defn restart []
  (stop)
  (start))


