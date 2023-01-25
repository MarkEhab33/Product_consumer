import { Globals } from "./Globals"
import Konva from 'konva';


export class Connection{


    points: number[] = []
    public prepareLine() {
        this.points=[]
        var x1 = Globals.stage.findOne('#' + Globals.firstShape).x()
        var y1 = Globals.stage.findOne('#' + Globals.firstShape).y()
        if (Globals.queues.includes(Globals.firstShape) == true)//found it in the rectangles
        {
          x1 = x1 + 35
          y1 = y1 + 25
        }
    
        var x2 = Globals.stage.findOne('#' + Globals.secondShape).x()
        var y2 = Globals.stage.findOne('#' + Globals.secondShape).y()
        if (Globals.queues.includes(Globals.secondShape) == true)//found it in the rectangles
        {
          x2 = x2 + 35
          y2 = y2 + 25
        }
        this.points.push(x1)
        this.points.push(y1)
        this.points.push(x2)
        this.points.push(y2)
        var lineID = Globals.linesCount.toString()
        Globals.linesCount = Globals.linesCount + 1
        var shapeIDs = []
        shapeIDs.push(Globals.firstShape)
        shapeIDs.push(Globals.secondShape)
        Globals.fromTo.set(lineID, shapeIDs)
        Globals.lines.push(lineID)
    
        
        Globals.linesOUT.get(Globals.firstShape)?.push(lineID)
        Globals.linesIN.get(Globals.secondShape)?.push(lineID)
    
        console.log("out of the first shape : " + Globals.linesOUT.get(Globals.firstShape));
        console.log("into the second shape : " + Globals.linesIN.get(Globals.secondShape));
    
        this.drawLine(this.points, lineID)
        console.log("from to=" + Globals.fromTo.keys.toString + " " + Globals.fromTo.values.toString)
        console.log("lines out=" + Globals.linesOUT)
        console.log("lines in=" + Globals.linesIN)
      }



      drawLine(p: number[], idLine: string) {

        var line = new Konva.Group({
          id: idLine
        })
    
        line.add(new Konva.Line({
          points: p,
          stroke: 'black',
          strokeWidth: 5,
          
        }))
    
        var arrowp=[]
        for(let a of p) arrowp.push(a)
        arrowp[2]=(arrowp[0]+arrowp[2])/2
        arrowp[3]=(arrowp[1]+arrowp[3])/2
    
        line.add(new Konva.Arrow({
          points: arrowp,
          pointerLength: 10,
          pointerWidth: 10,
          fill: 'black',
          stroke: 'black',
          strokeWidth: 4,
        }))
        
        
        let clickOnShape = () => {
          console.log('click line');
          Globals.deletedShape = idLine
        }
        line.on('click', () => { clickOnShape() })
    
    
        Globals.layer.add(line);
        Globals.layer.draw();
      }
    



}