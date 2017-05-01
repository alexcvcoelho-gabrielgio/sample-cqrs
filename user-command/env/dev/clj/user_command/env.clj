(ns user-command.env
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [user-command.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[user-command started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[user-command has shut down successfully]=-"))
   :middleware wrap-dev})
