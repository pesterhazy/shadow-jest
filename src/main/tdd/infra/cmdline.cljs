(ns tdd.infra.cmdline)

(defn create []
  {:process js/process})

(defn create-null []
  (let [!last-output (atom nil)]
    {:process #js{:stdout #js{:write (fn [s] (reset! !last-output s))}}
     :!last-output !last-output}))

(defn write [cmdline s]
  (-> ^js (:process cmdline) .-stdout (.write (str s "\n"))))

(defn last-output [cmdline]
  @(:!last-output cmdline))
