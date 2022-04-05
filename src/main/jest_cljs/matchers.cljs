(ns jest-cljs.matchers)

(defn toEq [received expected]
  (this-as
    ^js this
    (let [opts #js{:comment "clojure.core/= equality"
                   :isNot (.-isNot this)
                   :promise (.-promise this)}
          utils (.-utils this)
          pass (= expected received)
          message
          (fn print-toeq []
            (let [print-expected (fn [v] (.EXPECTED_COLOR utils (pr-str v)))
                  print-received (fn [v] (.RECEIVED_COLOR utils (pr-str v)))]
              (if pass
                (str
                 (-> this
                     .-utils
                     (.matcherHint "toEq" js/undefined js/undefined opts))
                 "\n\n"
                 "Expected: not "
                 (print-expected expected)
                 "\n"
                 "Received: "
                 (print-received received))
                (str
                 (-> this
                     .-utils
                     (.matcherHint "toEq" js/undefined js/undefined opts))
                 "\n\n"
                 "Expected: "
                 (print-expected expected)
                 "\n"
                 "Received: "
                 (print-received received)))))]
      #js{:actual received
          :message message
          :pass pass})))

(set! (.-toEq js/module.exports) toEq)
