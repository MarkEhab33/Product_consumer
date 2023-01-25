import { Globals } from "./Globals"
import Konva from 'konva';


export class Machine {


    public drawCircle() {
        var pos = Globals.stage.getRelativePointerPosition()
        var xMouse = pos.x
        var yMouse = pos.y
        var circleID = 'M' + Globals.circleCount.toString()
        var circle = new Konva.Group({
            x: xMouse,
            y: yMouse,
            id: circleID
        })
        circle.add(new Konva.Circle({
            x: 0,
            y: 0,
            radius: 30,
            fill: 'white',
            stroke: 'black',
            strokeWidth: 4,
            name:'shape',
        }))
        circle.add(new Konva.Text({
            x: -18,
            y: -15,
            text: circleID,
            fontSize: 20,
            padding: 5,
            align: 'center',
            fontFamily: 'Calibri',
            fill: 'black',
            stroke: '2px',
            name: 'label'
        }));

        Globals.linesIN.set(circleID, [])
        Globals.linesOUT.set(circleID, [])
        Globals.circleCount = Globals.circleCount + 1;
        Globals.layer.add(circle);
        Globals.machines.push(circleID)

        let clickOnShape = () => {
            if (Globals.firstShape === "") {
                Globals.firstShape = circleID
            } else {
                Globals.secondShape = circleID
            }
            Globals.deletedShape = circleID
        }
        circle.on('click', () => { clickOnShape() })
        Globals.layer.draw();
    }

}