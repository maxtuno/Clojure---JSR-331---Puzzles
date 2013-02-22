(ns cpwp.arch-friends
  (:use [cpwp.core])
  (:import [javax.constraints 
            Problem
            ProblemFactory
            Var]))

(def problem (ProblemFactory/newProblem "Arch Friends"))

(defn solution
  []
  (let [flats    (.variable problem "flats"    1 4)
        espa     (.variable problem "espa"     1 4)
        pumps    (.variable problem "pumps"    1 4)
        sandals  (.variable problem "sandals"  1 4)
        foot     (.variable problem "foot"     1 4)
        heels    (.variable problem "heels"    1 4)
        shoe     (.variable problem "shoe"     1 4)
        tootsies (.variable problem "tootsies" 1 4)]    
    (.postAllDifferent problem [flats espa pumps sandals])
    (.postAllDifferent problem [foot heels shoe tootsies])
    (.post problem foot "=" 2)
    (.post problem flats "=" heels)
    (.post problem (.plus pumps 1) "!=" tootsies)
    (.post problem (.plus shoe 2) "=" sandals)))

(solve problem solution)