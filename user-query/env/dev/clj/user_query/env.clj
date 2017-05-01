(ns user-query.env
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [user-query.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[user-query started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[user-query has shut down successfully]=-"))
   :middleware wrap-dev})
