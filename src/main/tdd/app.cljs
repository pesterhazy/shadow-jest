(ns tdd.app
  (:require [clojure.string :as str]))

(defn rot13 [s]
  (->> s
       (map #(js/String.fromCharCode (+ (.charCodeAt % 0) 13)))
       (str/join "")))
