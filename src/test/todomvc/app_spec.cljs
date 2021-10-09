(ns todomvc.app-spec)

(js/test "arithmetic"
         (fn []
           (-> (js/expect (+ 2 2))
               (.toBe 4))))
