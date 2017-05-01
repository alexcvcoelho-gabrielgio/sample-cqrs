(ns user-command.redis.core
  (:require [taoensso.carmine :as car :refer (wcar)]
            [clojure.data.json :as json]))


(def conn {})
(defmacro wcar* [& body] `(car/wcar conn ~@body))


(defn push [key data]
  (wcar* (car/lpush key (json/write-str data))))