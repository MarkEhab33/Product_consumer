import { Globals } from "./Globals"
import Konva from 'konva';
export class Queue {



    drawRect() {
        var pos = Globals.stage.getRelativePointerPosition()
        var xMouse = pos.x - 50
        var yMouse = pos.y - 25
        var rectID = 'Q' + Globals.rectCount.toString()
        var queueCount = 0

        var rect = new Konva.Group({
            x: xMouse,
            y: yMouse,
            id: rectID
        })

        rect.add(new Konva.Rect({
            x: 0,
            y: 0,
            width: 70,
            height: 50,
            fill: 'cyan',
            stroke: 'black',
            strokeWidth: 4,
            name: 'shape',
        }))
        rect.add(new Konva.Text({
            x: 0,
            y: 0,
            text: rectID,
            fontSize: 20,
            padding: 5,
            fontFamily: 'Calibri',
            fill: 'black',
            stroke: '2px',
            name: 'label'
        }));

        rect.add(new Konva.Text({
            x: 0,
            y: 20,
            text: queueCount.toString(),
            fontSize: 20,
            padding: 5,
            fontFamily: 'Calibri',
            fill: 'black',
            stroke: '2px',
            name: 'count'
        }));


        Globals.rectCount = Globals.rectCount + 1
        Globals.queues.push(rectID)
        Globals.linesIN.set(rectID, [])
        Globals.linesOUT.set(rectID, [])
        let clickOnShape = () => {
            console.log('click rect');
            console.log(rect.attrs.id)
            var selected = rect.findOne('.shape');
            //(<Shape>selected).fill("white")
            if (Globals.firstShape === "") {
                Globals.firstShape = rectID
                console.log("Got first id")
            } else {
                Globals.secondShape = rectID
                console.log("Got second id")

            }
            Globals.deletedShape = rectID
        }
        rect.on('click', () => { clickOnShape() })
        Globals.layer.add(rect);
        Globals.layer.draw();
    }







    initializeQueues(){
        this.createInitialRectangleWithID('Qi',window.innerWidth-300)
        this.createInitialRectangleWithID('Qx',200)
    }




    createInitialRectangleWithID(id:string,x:number){
        var xMouse = x - 50
        var yMouse = 300 - 25
        var rectID = id
        var queueCount = 0

        var rect = new Konva.Group({
            x: xMouse,
            y: yMouse,
            id: rectID
        })

        rect.add(new Konva.Rect({
            x: 0,
            y: 0,
            width: 70,
            height: 50,
            fill: 'cyan',
            stroke: 'black',
            strokeWidth: 4,
            name: 'shape',
        }))
        rect.add(new Konva.Text({
            x: 0,
            y: 0,
            text: rectID,
            fontSize: 20,
            padding: 5,
            fontFamily: 'Calibri',
            fill: 'black',
            stroke: '2px',
            name: 'label'
        }));

        rect.add(new Konva.Text({
            x: 0,
            y: 20,
            text: queueCount.toString(),
            fontSize: 20,
            padding: 5,
            fontFamily: 'Calibri',
            fill: 'black',
            stroke: '2px',
            name: 'count'
        }));


        Globals.queues.push(rectID)
        Globals.linesIN.set(rectID, [])
        Globals.linesOUT.set(rectID, [])
        let clickOnShape = () => {
            var selected = rect.findOne('.shape');
            //(<Shape>selected).fill("white")
            if (Globals.firstShape === "") {
                Globals.firstShape = rectID
            } else {
                Globals.secondShape = rectID
            }
            Globals.deletedShape = rectID
        }
        rect.on('click', () => { clickOnShape() })
        Globals.layer.add(rect);
        Globals.layer.draw();
    }
}