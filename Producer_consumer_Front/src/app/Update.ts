import { Shape } from "konva/lib/Shape"
import { Globals } from "./Globals"



export class Update{
    queueID:string=""
    machineID:string=""
    queueNum:string=""
    machineColour:string=""
    
    constructor(jsonStr:string){
        console.log(jsonStr)
        var obj=JSON.parse(jsonStr)
        this.machineID=obj.machineID
        this.machineColour=obj.machineColour
        this.queueID=obj.queueID
        this.queueNum=obj.queueNum
    }


    public execute(){
        var modifiedQ = Globals.stage.findOne('#' + this.queueID);
        (<Shape>modifiedQ.findOne('.count')).setAttr('text', this.queueNum);
        var modifiedM = Globals.stage.findOne('#' + this.machineID);
        console.log(this.machineColour);
        (<Shape>modifiedM.findOne('.shape')).fill(this.machineColour)
    }
}