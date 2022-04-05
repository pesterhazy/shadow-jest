(ns tdd.app-test
  (:require ["@jest/globals" :refer [test expect]]
            ["@testing-library/react" :as rtl]
            [uix.core.alpha :as uix]
            [tdd.app]))

(defn render-board-ui
  ([]
   (render-board-ui {}))
  ([opts]
   (rtl/render (uix/as-element [tdd.app/board-ui (merge {:fs tdd.app/empty-fs} opts)]))
   (rtl/screen.getAllByTestId "field")))

(test
 "shows start button"
 (fn []
   (rtl/render (uix/as-element [tdd.app/app-ui]))
   (-> (expect (rtl/screen.getByText "Start"))
       (.toBeInTheDocument))))

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
 "handles missing on-move handler gracefully"
 (fn []
   (let [fields (render-board-ui)]
     (rtl/fireEvent.click (get fields 2))
     (-> (expect) .pass))))
