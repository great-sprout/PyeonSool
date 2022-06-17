function addBestSlidingEvent(){
    const bestAlcoholCards = document.querySelector(".bestAlcoholList-list");
    const IMAGE_COUNT = bestAlcoholCards.childElementCount;
    const SLIDE_DISTANCE = 1180;
    const leftButton = document.querySelector(".bestAlcohol__leftmove");
    const rightButton = document.querySelector(".bestAlcohol__rightmove");

    const bestSlideController = new BestSlideController(10,bestAlcoholCards,IMAGE_COUNT,SLIDE_DISTANCE,leftButton,rightButton);
    
    leftButton.addEventListener("click",function(event){
        bestSlideController.slide(event.target);
    });
    rightButton.addEventListener("click",function(event){
        bestSlideController.slide(event.target);
    });
}

addBestSlidingEvent();