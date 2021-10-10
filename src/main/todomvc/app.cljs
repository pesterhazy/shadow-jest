(ns todomvc.app
  (:require [clojure.string :as str]
            [uix.core.alpha :as uix]))

(defn vec-remove [pos coll]
  (into (subvec coll 0 pos) (subvec coll (inc pos))))

(def the-filters [:all :active :completed])

;; FIXME: terrible name
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
                         editing
                         on-edit
                         on-edit-submit
                         on-toggle
                         on-destroy]}]
  (let [!wip (uix/state "")
        !input (uix/ref)]
    (uix/effect! (fn []
                   (when @!input
                     (.focus @!input))
                   js/undefined))
    (->> todos
         (map-indexed
          (fn [idx {:keys [label completed]}]
            [:li {:class [(when completed "completed")
                          (when (= editing idx) "editing")]}
             [:div.view
              [:input.toggle {:data-testid (str "toggle-" idx)
                              :type "checkbox"
                              :checked (boolean completed)
                              :on-change
                              (fn []
                                (on-toggle idx))}]
              [:label {:data-testid (str "item-" idx)
                       :on-double-click (fn []
                                          (reset! !wip (get-in todos [idx :label]))
                                          (on-edit idx))}
               label]
              [:button.destroy {:data-testid "destroy"
                                :on-click
                                (fn []
                                  (on-destroy idx))}]]
             [:input.edit {:type "text"
                           :data-testid (str "edit-" idx)
                           :value @!wip
                           :ref !input
                           :on-change (fn [ev]
                                        (reset! !wip (-> ev .-target .-value)))
                           :on-key-down (fn [ev]
                                          (when (= "Enter" (.-key ev))
                                            (let [s (str/trim (.-value @!input))]
                                              (when (seq s)
                                                (on-edit-submit idx s)))))}]]))
         (into [:ul.todo-list]))))

;; FIXME: rename other components to -ui?

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

(defn toggle-all-ui [{:keys [checked
                             on-change]}]
  [:span
   [:input.toggle-all#toggle-all {:type "checkbox"
                                  :checked (boolean checked)
                                  :on-change (fn []
                                               (on-change (not checked)))
                                  :data-testid "toggle-all"}]
   [:label {:for :toggle-all}]])

(defn clear-completed-ui [{:keys [on-click]}]
  [:button.clear-completed {:on-click on-click}
   "Clear completed"])

(defn app [{:keys [initial-todos]}]
  (assert (vector? initial-todos))
  (let [!todos (uix/state initial-todos)
        !filter (uix/state :all)
        !editing (uix/state nil)
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
     (when (seq @!todos)
       [:section.main {:data-testid "main"}
        [toggle-all-ui {:checked (if (->> @!todos
                                          (every? :completed))
                                   true
                                   false)
                        :on-change
                        (fn [checked]
                          (swap! !todos
                                 #(->> %
                                       (mapv (fn [todo]
                                               (assoc todo :completed checked))))))}]
        [todo-list {:todos visible-todos
                    :editing @!editing
                    :on-edit (fn [idx]
                               (reset! !editing idx))
                    :on-edit-submit (fn [idx s]
                                      (swap! !todos assoc-in [idx :label] s)
                                      (reset! !editing nil))
                    :on-toggle (fn [idx] (swap! !todos update-in [idx :completed] not))
                    :on-destroy (fn [idx] (swap! !todos (partial vec-remove idx)))}]])
     (when (seq @!todos)
       [:footer.footer {:data-testid "footer"}
        [:span.todo-count (str active-count
                               " "
                               (if (= 1 active-count)
                                 "item"
                                 "items")
                               " left")]
        [filter-ui {:value @!filter
                    :on-change (fn [filter] (reset! !filter filter))}]
        [clear-completed-ui {:on-click
                             (fn []
                               (swap! !todos
                                      #(->> %
                                            (filterv (complement :completed)))))}]])]))
