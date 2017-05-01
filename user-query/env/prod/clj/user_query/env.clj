(ns user-query.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[user-query started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[user-query has shut down successfully]=-"))
   :middleware identity})
