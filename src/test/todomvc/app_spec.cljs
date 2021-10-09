(ns todomvc.app-spec)

(js/console.log "XXX")
(js/throw (js/Error. "boom"))

;; test('two plus two is four', () => {
;; expect(2 + 2).toBe(4);
;;});

(js/test "arithmetic"
         (fn []
           (-> (js/expect (+ 2 2))
               (.toBe 5))))
