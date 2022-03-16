(ns tdd.app
  (:require [clojure.string :as str]))

(def alphabet-start (.charCodeAt "a" 0) )
(def alphabet-end (.charCodeAt "z" 0) )

(defn xform [c]
  (let [n (.charCodeAt c 0)]
    (if (and (>= n alphabet-start) (<= n alphabet-end))
      (js/String.fromCharCode (+ (mod (+ (- n alphabet-start) 13) 26)
                                 alphabet-start))
      c)))

(defn rot13 [s]
  (->> s
       (map xform)
       (str/join "")))
