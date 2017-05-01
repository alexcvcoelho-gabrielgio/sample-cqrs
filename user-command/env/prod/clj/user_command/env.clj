(ns user-command.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[user-command started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[user-command has shut down successfully]=-"))
   :middleware identity})
