(ns tdd.app-test
  (:require ["@jest/globals" :refer [test expect]]
            ["@testing-library/react" :as rtl]
            [uix.core.alpha :as uix]
            [tdd.app]))

(test
 "shows start button"
 (fn []
   (rtl/render (uix/as-element [tdd.app/app-ui]))
   (-> (expect (rtl/screen.getByText "Start"))
       (.toBeInTheDocument))))

(test
 "shows board with 9 fields"
 (fn []
   (rtl/render (uix/as-element [tdd.app/board-ui]))
   (let [fields (rtl/screen.getAllByTestId "field")]
     (-> (expect (count fields))
         (.toBe 9)))))
(test
 "shows correct fields"
 (fn []
   (let [fs ["O" "X" "X" "" "O" "O" "X" "O" "O"]
         _ (rtl/render (uix/as-element [tdd.app/board-ui {:fs fs}]))
         fields (rtl/screen.getAllByTestId "field")]
     (-> (expect (count fields))
         (.toBe 9))
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
   (let [fs ["" "" "" "" "" "" "" "" ""]
         !n (atom nil)
         _ (rtl/render (uix/as-element [tdd.app/board-ui
                                        {:fs fs
                                         :on-move (fn [n] (reset! !n n))}]))
         fields (rtl/screen.getAllByTestId "field")]
     (rtl/fireEvent.click (get fields 2))

     (-> (expect @!n) (.toBe 2)))))

(test
 "handles missing on-move handler gracefully"
 (fn []
   (let [fs ["" "" "" "" "" "" "" "" ""]
         _ (rtl/render (uix/as-element [tdd.app/board-ui {:fs fs}]))
         fields (rtl/screen.getAllByTestId "field")]
     (rtl/fireEvent.click (get fields 2))
     (-> (expect) .pass))))
