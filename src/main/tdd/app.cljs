(ns tdd.app
  (:require [clojure.string :as str]))

(def alphabet-lower-start (.charCodeAt "a" 0) )
(def alphabet-lower-end (.charCodeAt "z" 0) )

(def alphabet-upper-start (.charCodeAt "A" 0) )
(def alphabet-upper-end (.charCodeAt "Z" 0) )

(defn xform [c]
  (let [n (.charCodeAt c 0)]
    (cond
      (and (>= n alphabet-lower-start) (<= n alphabet-lower-end))
      (js/String.fromCharCode (+ (mod (+ (- n alphabet-lower-start) 13) 26)
                                 alphabet-lower-start))
      (and (>= n alphabet-upper-start) (<= n alphabet-upper-end))
      (js/String.fromCharCode (+ (mod (+ (- n alphabet-upper-start) 13) 26)
                                 alphabet-upper-start))
      :else
      c)))

(defn rot13 [s]
  (->> s
       (map xform)
       (str/join "")))
