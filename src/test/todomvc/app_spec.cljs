(ns todomvc.app-spec
  (:require ["@testing-library/react" :as rtl]
            [uix.core.alpha :as uix]
            [todomvc.app :as x]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; test helpers

(defn render
  ([]
   (render {}))
  ([opts]
   (rtl/render (uix/as-element [x/app {:initial-todos (or
                                                       (:initial-todos opts)
                                                       [{:label "A"}])}]))))

(defn query-all-labels []
  (->> (rtl/screen.queryAllByTestId #"item")
       (map #(.-textContent %))))

(defn get-new-todo []
  (rtl/screen.getByPlaceholderText "What needs to be done?"))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; tests

(js/test "initial screen"
         (fn []
           (render)
           (-> (js/expect (rtl/screen.getByRole "heading"))
               (.toHaveTextContent "todos"))
           (-> (js/expect (rtl/screen.getByPlaceholderText "What needs to be done?"))
               (.toBeInTheDocument))
           (-> (js/expect (rtl/screen.getByText "A"))
               (.toBeInTheDocument))
           (-> (js/expect (rtl/screen.getByText "1 item left"))
               (.toBeInTheDocument))
           (-> (js/expect (rtl/screen.getByText "All"))
               (.toHaveClass "selected"))
           (-> (js/expect (rtl/screen.getByText "Active"))
               (.-not)
               (.toHaveClass "selected"))
           (-> (js/expect (rtl/screen.getByText "Completed"))
               (.-not)
               (.toHaveClass "selected"))))

(js/test "add todo"
         (fn []
           (render)
           (rtl/fireEvent.change (get-new-todo)
                                 #js{:target #js{:value "B"}})
           (rtl/fireEvent.keyDown (get-new-todo)
                                  #js{:key "Enter" :code 13 :charCode 13})
           (-> (js/expect (rtl/screen.getByText "B"))
               (.toBeInTheDocument))
           (-> (js/expect (-> (get-new-todo) .-value))
               (.toBe ""))
           (-> (js/expect (rtl/screen.getByText "2 items left"))
               (.toBeInTheDocument))))

;; FIXME: custom matcher for CLJS data structures?

(defn explain-not= [a b]
  (if (= a b)
    nil
    (str "Not the same: " (pr-str [a b]))))

(js/test "can't create empty todo"
         (fn []
           (render)
           (rtl/fireEvent.keyDown (get-new-todo)
                                  #js{:key "Enter" :code 13 :charCode 13})
           (-> (js/expect (explain-not= (query-all-labels) ["A"]))
               (.toBeNull))))

(js/test "remove todo"
         (fn []
           (render)
           (rtl/fireEvent.click (rtl/screen.getByTestId "destroy"))
           (-> (js/expect (rtl/screen.queryByText "A"))
               .toBeNull)
           (-> (js/expect (rtl/screen.queryByTestId "main"))
               .toBeNull)
           (-> (js/expect (rtl/screen.queryByTestId "footer"))
               .toBeNull)))

(js/test "complete todo"
         (fn []
           (render)
           (rtl/fireEvent.click (rtl/screen.getByTestId "toggle-0"))
           (-> (js/expect (-> (rtl/screen.getByText "A")
                              (.closest "li")))
               (.toHaveClass "completed"))
           (-> (js/expect (rtl/screen.getByText "0 items left"))
               (.toBeInTheDocument))
           (rtl/fireEvent.click (rtl/screen.getByText "Clear completed"))
           (-> (js/expect (explain-not= [] (query-all-labels)))
               (.toBeNull))))

(js/test "select filter"
         (fn []
           (render {:initial-todos [{:label "A" :completed false}
                                    {:label "B" :completed true}
                                    {:label "C" :completed false}]})
           (-> (js/expect (explain-not= ["A" "B" "C"] (query-all-labels)))
               (.toBeNull))
           (rtl/fireEvent.click (rtl/screen.getByTestId "filter-active"))
           (-> (js/expect (rtl/screen.getByText "All"))
               (.-not)
               (.toHaveClass "selected"))
           (-> (js/expect (rtl/screen.getByText "Active"))
               (.toHaveClass "selected"))
           (-> (js/expect (explain-not= ["A" "C"] (query-all-labels)))
               (.toBeNull))
           (rtl/fireEvent.click (rtl/screen.getByTestId "filter-completed"))
           (-> (js/expect (rtl/screen.getByText "Completed"))
               (.toHaveClass "selected"))
           (-> (js/expect (explain-not= ["B"] (query-all-labels)))
               (.toBeNull))))

(js/test "toggle all"
         (fn []
           (render {:initial-todos [{:label "A" :completed false}
                                    {:label "B" :completed false}]})
           (rtl/fireEvent.click (rtl/screen.getByTestId "toggle-all"))
           (-> (js/expect (-> (rtl/screen.getByText "A")
                              (.closest "li")))
               (.toHaveClass "completed"))
           (-> (js/expect (-> (rtl/screen.getByText "B")
                              (.closest "li")))
               (.toHaveClass "completed"))))

(js/test "edit todo"
         (fn []
           (render)
           (-> (js/expect (-> (rtl/screen.getByText "A")
                              (.closest "li")))
               .-not
               (.toHaveClass "editing"))
           (rtl/fireEvent.doubleClick (rtl/screen.getByTestId "item-0"))
           (let [li (-> (rtl/screen.getByText "A")
                        (.closest "li"))]
             (-> (js/expect li)
                 (.toHaveClass "editing"))
             (-> (js/expect (rtl/screen.getByTestId "edit-0"))
                 (.toHaveValue "A")))))
