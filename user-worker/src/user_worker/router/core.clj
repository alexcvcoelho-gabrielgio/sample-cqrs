(ns user-worker.router.core
  (:require [clojure.data.json :as json]
            [user-worker.db.core :as db :refer [create-user log-done]]))

(defn parse-and-create-user [cmd]
  (create-user {:name  (:name cmd)
                :email (:email cmd)
                :uid   (:uid cmd)})
  (log-done (:trackId cmd)))

(defn process-string [s t]
  (let [cmd (json/read-str (nth s 1) :key-fn keyword)]
    (case (:command cmd)
      "create_user_command" (parse-and-create-user cmd)
      "default" (println "Command not found"))))