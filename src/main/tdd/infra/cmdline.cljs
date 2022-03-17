(ns tdd.infra.cmdline)

(defn create []
  {:process js/process})

(defn create-null
  ([] (create-null {}))
  ([opts]
   (let [!last-output (atom nil)]
     {:process #js{:stdout #js{:write (fn [s] (reset! !last-output s))}
                   :argv (into-array (concat ["x" "x"] (:args opts)))}
      :!last-output !last-output})))

(defn write [cmdline s]
  (-> ^js (:process cmdline) .-stdout (.write (str s "\n"))))

(defn args [cmdline]
  (-> ^js (:process cmdline) .-argv (.slice 2) vec))

(defn last-output [cmdline]
  @(:!last-output cmdline))
