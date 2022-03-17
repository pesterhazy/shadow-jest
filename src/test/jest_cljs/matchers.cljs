(ns jest-cljs.matchers)

(defn toEq [received expected]
  (this-as
    ^js this
    (let [opts #js{:comment "clojure.core/= equality"
                   :isNot (.-isNot this)
                   :promise (.-promise this)}
          pass (= expected received)
          message
          (fn [] (if pass
                   (str
                    (-> this
                        .-utils
                        (.matcherHint "toEq" js/undefined js/undefined opts))
                    "\n\n"
                    "Expected: not "
                    (-> this .-utils (.printExpected (pr-str expected)))
                    "\n"
                    "Received: "
                    (-> this .-utils (.printReceived (pr-str received))))
                   (str
                    (-> this
                        .-utils
                        (.matcherHint "toEq" js/undefined js/undefined opts))
                    "\n\n"
                    "Expected: "
                    (-> this .-utils (.printExpected (pr-str expected)))
                    "\n"
                    "Received: "
                    (-> this .-utils (.printReceived (pr-str received))))))]
      #js{:actual received
          :message message
          :pass pass})))

(set! (.-toEq js/module.exports) toEq)
