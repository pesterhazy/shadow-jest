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

(defn todo-list [{:keys [todos
                         on-toggle
                         on-destroy]}]
  (->> todos
       (map-indexed
        (fn [idx {:keys [label completed]}]
          [:li {:class (when completed "completed")}
           [:div.view
            [:input.toggle {:data-testid (str "toggle-" idx)
                            :type "checkbox"
                            :checked (boolean completed)
                            :on-change
                            (fn []
                              (on-toggle idx))}]
            [:label {:data-testid (str "item-" idx)}
             label]
            [:button.destroy {:data-testid "destroy"
                              :on-click
                              (fn []
                                (on-destroy idx))}]]]))
       (into [:ul.todo-list])))

(defn filter-ui [{:keys [value on-change]}]
  (->> the-filters
       (map (fn [filter]
              [:li {:key (name filter)}
               [:a {:style {:cursor :pointer}
                    :class (when (= filter value) "selected")
                    :data-testid (str "filter-" (name filter))
                    :on-click (fn []
                                (on-change filter))}
                (str/capitalize (name filter))]]))
       (into [:ul.filters])))

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
      [search-box {:on-submit (fn [s] (swap! !todos conj {:label s}))}]]
     [:section.main
      [:span
       [:input.toggle-all {:type "checkbox" :read-only true}]
       [:label]]
      [todo-list {:todos visible-todos
                  :on-toggle (fn [idx] (swap! !todos update-in [idx :completed] not))
                  :on-destroy (fn [idx] (swap! !todos (partial vec-remove idx)))}]]
     (when (seq @!todos)
       [:footer.footer {:data-testid "footer"}
        [:span.todo-count (str active-count
                               " "
                               (if (= 1 active-count)
                                 "item"
                                 "items")
                               " left")]
        [filter-ui {:value @!filter
                    :on-change (fn [filter] (reset! !filter filter))}]])]))
