(ns tdd.app
  (:require [clojure.string :as str]))

(defn rot13 [s]
  (->> s
       (map #(js/String.fromCharCode (+ (mod (+ (- (.charCodeAt % 0)
                                                   (.charCodeAt "a" 0)) 13)
                                             26)
                                        (.charCodeAt "a" 0))))
       (str/join "")))
