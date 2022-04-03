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

(defn find-delim [s]
  (if-let [m (re-matches #"//(.)\n(.*)" s)]
    [(m 2) (m 1)]
    (if-let [m (re-matches #"//\[(.+)\]\n(.*)" s)]
      [(m 2) (m 1)]
      nil)))

(defn add
  ([s] (add s ","))
  ([s delim]
   (if (empty? s)
     0
     (if-let [[new-s delim] (find-delim s)]
       (add new-s delim)
       (let [numbers
             (->> (.split s (re-pattern (str (escape delim) "|\n")))
                  (map parse-number))
             negs
             (->> numbers (filter neg?))]
         (when (seq negs)
           (throw (js/Error. (str "Negative numbers not allowed: "
                                  (->> negs (string/join ", "))))))
         (->> numbers
              (remove #(> % 1000))
              (reduce + 0)))))))
