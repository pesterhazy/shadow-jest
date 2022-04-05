(ns hashpr.core)

;; Define tag functions. See data_readers.clj
;; Also see docs/client/hashpr.md
(defn hashpr [form]
  (let [result-sym (gensym "result")]
    `(let [~result-sym ~form]
       (js/console.warn
        (binding [clojure.core/*print-length* 20]
          (str (pr-str '~form) " => "
               (pr-str ~result-sym))))
       ~result-sym)))

(defn hashpr! [form]
  (let [result-sym (gensym "result")]
    `(let [~result-sym ~form]
       (js/console.warn
        (str (pr-str '~form) " => "
             (pr-str ~result-sym)))
       ~result-sym)))

(defn hashpp [form]
  (let [result-sym (gensym "result")]
    `(let [~result-sym ~form]
       (js/console.warn
        (binding [clojure.core/*print-length* 20]
          (str "***\n" ;; start with newline for indentation
               (with-out-str (cljs.pprint/pprint '~form))
               "=>\n"
               (with-out-str (cljs.pprint/pprint ~result-sym)))))
       ~result-sym)))

(defn hashpp! [form]
  (let [result-sym (gensym "result")]
    `(let [~result-sym ~form]
       (js/console.warn
        (str "***\n" ;; start with newline for indentation
             (with-out-str (cljs.pprint/pprint '~form))
             "=>\n"
             (with-out-str (cljs.pprint/pprint ~result-sym))))
       ~result-sym)))

(defn hashpc [form]
  (let [result-sym (gensym "result")]
    `(let [~result-sym ~form]
       (js/console.warn
        '~form " => "
        ~result-sym)
       ~result-sym)))

(defn hashp [form]
  `(let [result-sym# ~form]
     (js/console.log result-sym#)
     result-sym#))

(defn hashp-tap [form]
  `(let [result-sym# ~form]
     (tap> result-sym#)
     result-sym#))
