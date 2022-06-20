function addBestSlidingEvent(){
    const bestAlcoholCards = document.querySelector(".bestAlcoholList-list");
    const IMAGE_COUNT = bestAlcoholCards.childElementCount;
    const SLIDE_DISTANCE = 305;
    const leftButton = document.querySelector(".bestAlcohol__leftmove");
    const rightButton = document.querySelector(".bestAlcohol__rightmove");

    // const bestSlideController = new BestSlideController(10,bestAlcoholCards,IMAGE_COUNT,SLIDE_DISTANCE,leftButton,rightButton);
    const bestSlideController = new SlideController(0, bestAlcoholCards,IMAGE_COUNT, 1, SLIDE_DISTANCE, leftButton,rightButton);
    
    leftButton.addEventListener("click",function(event){
        bestSlideController.slide(event.target, 4);
    });
    rightButton.addEventListener("click",function(event){
        bestSlideController.slide(event.target, 4);
    });
}
function addButtonClick(){
   
    const selectButton=document.querySelector(".keyword-button");
    selectButton.addEventListener("click",function(){
        
        console.log(selectButton);
        selectButton.classList.toggle("keyword-button--clicked");
    });

}
addButtonClick();
addBestSlidingEvent();