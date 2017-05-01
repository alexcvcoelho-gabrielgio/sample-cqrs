(ns user-worker.redis.core
  (:require [taoensso.carmine :as car :refer (wcar)]
            [clojure.data.json :as json]))


(def conn {})
(defmacro wcar* [& body] `(car/wcar conn ~@body))


(defn pull [key]
  (wcar* (car/brpop key 0)))