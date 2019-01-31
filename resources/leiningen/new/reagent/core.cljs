(ns {{namespace}}
  (:require [reagent.core :as r]))

(defonce timer
  (r/atom (js/Date.)))

(defonce time-color
  (r/atom "red"))

(defonce time-updater
  (js/setInterval
   (fn []
     (reset! timer (js/Date.)))
   1000))

(defn greeting [message]
  [:h1 message])

(defn clock []
  (let [time-str (-> @timer
                     .toTimeString
                     (clojure.string/split " ")
                     first)]
    [:div.example-clock
     {:style {:color @time-color}}
     time-str]))

(defn color-input []
  [:div.color-input
   "Time color: "
   [:input {:type "text"
            :value @time-color
            :on-change (fn [event]
                         (reset! time-color (-> event .-target .-value)))}]])

(defn page []
  [:div
   [greeting "Hello world, it is now"]
   [clock]
   [color-input]])

(r/render-component [page] (.querySelector js/document "#content"))
