(ns tdd.ui.board-test
  (:require
   ["@jest/globals" :refer [expect test]]
   ["@testing-library/react" :as rtl]
   [tdd.ui.board :as board]
   [uix.core.alpha :as uix]))

(defn render-board-ui
  ([]
   (render-board-ui {}))
  ([opts]
   (rtl/render (uix/as-element [board/board-ui (merge {:fs board/empty-fs} opts)]))
   (rtl/screen.getAllByTestId "field")))

(test
 "shows board with 9 fields"
 (fn []
   (let [fields (render-board-ui)]
     (-> (expect (count fields))
         (.toBe 9)))))
(test
 "shows correct fields"
 (fn []
   (let [fs ["O" "X" "X" "" "O" "O" "X" "O" "O"]
         fields (render-board-ui {:fs fs})]
     (-> (expect (first fields))
         (.toHaveClass "box"))
     (-> (expect (.-parentElement (first fields)))
         (.toHaveClass "board"))
     (-> (expect (->> fields
                      (map #(-> % .-innerHTML))
                      vec))
         (.toEq fs)))))

(test
 "allows user to click on field"
 (fn []
   (let [!n (atom nil)
         fields (render-board-ui {:on-move (fn [n] (reset! !n n))})]
     (rtl/fireEvent.click (get fields 2))
     (-> (expect @!n) (.toBe 2)))))

(test
 "doesn't let user click on used field"
 (fn []
   (let [!n (atom nil)
         fields (render-board-ui {:fs ["" "" "X" "" "" "" "" "" ""]
                                  :on-move (fn [n] (reset! !n n))})]
     (rtl/fireEvent.click (get fields 2))
     (-> (expect @!n) (.toEq nil)))))

(test
 "handles missing on-move handler gracefully"
 (fn []
   (let [fields (render-board-ui)]
     (rtl/fireEvent.click (get fields 2))
     (-> (expect) .pass))))

(test
 "doesn't let user click when frozen"
 (fn []
   (let [!n (atom nil)
         fields (render-board-ui {:fs ["" "" "" "" "" "" "" "" ""]
                                  :frozen true
                                  :on-move (fn [n] (reset! !n n))})]
     (rtl/fireEvent.click (get fields 2))
     (-> (expect @!n) (.toEq nil)))))

(test
 "highlights fields"
 (fn []
   (let [fs ["O" "X" "X" "O" "" "O" "O" "X" "O"]
         fields (render-board-ui {:fs fs
                                  :highlight #{0 3 6}})]
     (-> (expect (->> fields
                      (keep-indexed (fn [idx el]
                                      (when (-> el .-classList (.contains "highlight"))
                                        idx)))
                      set))
         (.toEq #{0 3 6})))))
