(ns tdd.logic.calc
  (:require ["regex-escape" :as escape]
            [clojure.string :as string]))

(defn parse-number [s]
  (if (= s "")
    (throw (js/Error. (str "Invalid input: " s)))
    (let [r (js/Number s)]
      (when (js/isNaN r)
        (throw (js/Error. (str "Invalid input: " s))))
      r)))

(defn find-delims
  "Returns nil or [s delims]"
  [s]
  (if-let [m (re-matches #"//(.)\n(.*)" s)]
    [(m 2) [(m 1)]]
    (if-let [[_ delim-line s] (re-matches #"//(\[.+\])\n(.*)" s)]
      [s (->> delim-line
              (re-seq #"\[(.+?)\]")
              (map second)
              vec)]
      nil)))

(defn add
  ([s] (add s [","]))
  ([s delims]
   (if (empty? s)
     0
     (if-let [[new-s new-delims] (find-delims s)]
       (add new-s new-delims)
       (let [regex (str (->> delims
                             (map escape)
                             (string/join "|"))
                        "|\n")
             numbers
             (->> (.split s
                          (re-pattern regex))
                  (map parse-number))
             negs
             (->> numbers (filter neg?))]
         (when (seq negs)
           (throw (js/Error. (str "Negative numbers not allowed: "
                                  (->> negs (string/join ", "))))))
         (->> numbers
              (remove #(> % 1000))
              (reduce + 0)))))))
