(ns user-worker.core
  (:gen-class)
  (:require [clojure.core.async :as a :refer [go <!! <! >! >!! thread chan]]
            [user-worker.redis.core :refer [pull]]
            [user-worker.router.core :refer [process-string]]
            [user-worker.db.core :as db :refer [create-schema]]))

(import 'java.lang.Runtime)

(defn get-core-count []
  (-> (Runtime/getRuntime) .availableProcessors))

(defn runner [c t]
  (loop []
    (when true
      (process-string (<!! c) t)
      (recur))))

(defn puller [ch]
  (loop []
    (when true
      (>!! ch (pull "user"))
      (recur))))

(defn start-workers []
  (let [c (get-core-count)
        bch (chan 4)]
    (doseq [x (range (inc c))]
      (thread (runner bch x)))
    (puller bch)))

(defn -main [& args]
  (create-schema)
  (start-workers))