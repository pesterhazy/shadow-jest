(ns todomvc.app
  (:require [clojure.string :as str]
            [uix.core.alpha :as uix]))

(defn vec-remove [pos coll]
  (into (subvec coll 0 pos) (subvec coll (inc pos))))

(def the-filters [:all :active :completed])

(defn search-box [{:keys [on-submit]}]
  (let [!input (uix/ref nil)]
    [:input.new-todo {:type "text"
                      :placeholder "What needs to be done?"
                      :auto-focus true
                      :on-key-down (fn [ev]
                                     (when (= "Enter" (.-key ev))
                                       (let [s (str/trim (.-value @!input))]
                                         (when (seq s)
                                           (on-submit s)
                                           (set! (.-value @!input) "")))))
                      :ref !input}]))

(defn app [{:keys [initial-todos]}]
  (assert (vector? initial-todos))
  (let [!todos (uix/state initial-todos)
        !filter (uix/state :all)
        visible-todos (case @!filter
                        :all
                        @!todos
                        :active
                        (->> @!todos
                             (remove :completed))
                        :completed
                        (->> @!todos
                             (filter :completed)))
        active-count (->> @!todos
                          (remove :completed)
                          count)]

    [:div
     [:header.header
      [:h1 "todos"]
      [search-box {:on-submit (fn [s] (swap! !todos conj {:label s}))}]
      ]
     [:section.main
      [:span
       [:input.toggle-all {:type "checkbox" :read-only true}]
       [:label]]
      (->> visible-todos
           (map-indexed
            (fn [idx {:keys [label completed]}]
              [:li {:class (when completed "completed")}
               [:div.view
                [:input.toggle {:data-testid (str "toggle-" idx)
                                :type "checkbox"
                                :checked (boolean completed)
                                :on-change
                                (fn []
                                  (swap! !todos update-in [idx :completed] not))}]
                [:label {:data-testid (str "item-" idx)}
                 label]
                [:button.destroy {:data-testid "destroy"
                                  :on-click
                                  (fn []
                                    (swap! !todos (partial vec-remove idx)))}]]]))
           (into [:ul.todo-list]))]
     (when (seq @!todos)
       [:footer.footer {:data-testid "footer"}
        [:span.todo-count (str active-count
                               " "
                               (if (= 1 active-count)
                                 "item"
                                 "items")
                               " left")]
        (->> the-filters
             (map (fn [filter]
                    [:li {:key (name filter)}
                     [:a {:style {:cursor :pointer}
                          :class (when (= filter @!filter) "selected")
                          :data-testid (str "filter-" (name filter))
                          :on-click (fn []
                                      (reset! !filter filter))}
                      (str/capitalize (name filter))]]))
             (into [:ul.filters]))])]))
