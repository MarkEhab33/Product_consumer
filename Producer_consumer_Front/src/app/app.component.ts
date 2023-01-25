
import { Component } from '@angular/core';
import Konva from 'konva';
import { Globals } from './Globals';
import { Connection } from './Connection';
import { Machine } from './Machine';
import { Queue } from './Queue';
import { WebSocketAPI } from './WebSocketApi';
import { Update } from './Update';
import { HttpClient } from '@angular/common/http';
import { Shape } from 'konva/lib/Shape';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'lab5';
  modes: number = 0
  webSocketAPI: any;
  rate:string=""

  sendDataURL = "http://localhost:8080/productconsumer/data"
  startSimURL = "http://localhost:8080/productconsumer/start"
  replaySimURL = "http://localhost:8080/productconsumer/replay"
  stopSimURL = "http://localhost:8080/productconsumer/end"





  /**
   * mode 0:None
   * mode 1:adding Queue
   * mode 2:adding Machine
   * Mode 3:adding Line 
   * Mode 4:deleting 
   * mode 5:simuating
   * mode 6:replaying
   *  
   */



  constructor(private httpClient: HttpClient) { }
  ngOninit() {

  }
  ngAfterViewInit() {

    Globals.stage = new Konva.Stage({
      container: 'container',   // id of container <div>
      width: window.innerWidth
      , height: 600
    });
    Globals.layer = new Konva.Layer();
    Globals.stage.add(Globals.layer);

    var initials: Queue = new Queue()
    initials.initializeQueues()
    //  var Stomp = require("stompjs/lib/stomp.js").Stomp
    this.webSocketAPI = new WebSocketAPI(this);
  }



  onQclick() {
    if (this.modes === 1) {
      this.modes = 0;
      Globals.secondShape=''
      Globals.firstShape=''
      Globals.deletedShape=''
    }
    else
      this.modes = 1;
  }

  onMclick() {
    if (this.modes === 2) {
      this.modes = 0;
      Globals.secondShape=''
      Globals.firstShape=''
      Globals.deletedShape=''

    }
    else
      this.modes = 2;
  }



  connect() {
    this.webSocketAPI._connect()
  }


  onLclick() {
    if (this.modes === 3) {
      this.modes = 0;
      Globals.secondShape=''
      Globals.firstShape=''
      Globals.deletedShape=''
    }
    else
      this.modes = 3;
  }



  onDelete() {
    if (this.modes === 4) {
      this.modes = 0;
      Globals.secondShape=''
      Globals.firstShape=''
      Globals.deletedShape=''
    }
    else
      this.modes = 4;
  }



  clickScreen() {
    switch (this.modes) {
      case 0:
        Globals.firstShape = ""
        Globals.secondShape = ""
        Globals.deletedShape = ""
        break;
      case 1:
        Globals.firstShape = ""
        Globals.secondShape = ""
        Globals.deletedShape = ""
        var queue: Queue = new Queue()
        queue.drawRect();
        this.modes = 0
        break;
      case 2:
        Globals.firstShape = ""
        Globals.secondShape = ""
        Globals.deletedShape = ""
        var machine: Machine = new Machine();
        machine.drawCircle();
        this.modes = 0
        break;
      case 3:
        if (!(Globals.firstShape === "" || Globals.secondShape === "")) {
          if ((Globals.machines.includes(Globals.firstShape) == true && Globals.machines.includes(Globals.secondShape) == true)
            || (Globals.queues.includes(Globals.firstShape) == true && Globals.queues.includes(Globals.secondShape) == true)
            || (Globals.firstShape === 'Qx') || (Globals.secondShape === 'Qi') 
            ||  Globals.machines.includes(Globals.firstShape) && Globals.linesOUT.get(Globals.firstShape)?.length===1 ) {
            Globals.firstShape = ""
            Globals.secondShape = ""
            Globals.deletedShape = ""
            alert("Unallowed Selection")
            return
          }
          var line: Connection = new Connection()
          line.prepareLine()
          var shape1 = Globals.stage.findOne('#' + Globals.firstShape);
          var shape2 = Globals.stage.findOne('#' + Globals.secondShape);
          shape1.moveToTop()
          shape2.moveToTop()
          Globals.layer.draw()
          this.modes = 0
          Globals.firstShape = ""
          Globals.secondShape = ""
          Globals.deletedShape = ""
          this.refresh()
        }
        

        break;

      case 4:
        if (Globals.deletedShape != "") {
          if (Globals.deletedShape === 'Qi' || Globals.deletedShape === 'Qx') {
            alert("Can't Delete These Queues!")
            Globals.deletedShape = ''
            Globals.firstShape=''
            Globals.secondShape=''
            return
          }

          if (Globals.deletedShape.includes("M")) this.deleteElementFromArray(Globals.machines, Globals.deletedShape)
          else if (Globals.deletedShape.includes("Q")) this.deleteElementFromArray(Globals.queues, Globals.deletedShape)
          else this.deleteElementFromArray(Globals.lines, Globals.deletedShape)


          if (Globals.deletedShape.includes("M") || Globals.deletedShape.includes("Q")) {
            this.deleteConnectionsOfShape(Globals.deletedShape)
            Globals.stage.findOne('#' + Globals.deletedShape).destroy()
          }
          else { //line
            this.deleteConnectionsOfaLine(Globals.deletedShape)
          }
        }
        this.modes = 0
        Globals.deletedShape = ""
        break

      case 5:
        this.modes = 0
        Globals.secondShape=''
        Globals.firstShape=''
        Globals.deletedShape=''
        break;
      case 6:
        this.modes = 0
        Globals.secondShape=''
        Globals.firstShape=''
        Globals.deletedShape=''
        break;
    }
  }

  refresh() {
    Globals.layer.draw();
  }

  getMousePosition(): any {
    return Globals.stage.getRelativePointerPosition()
  }



  deleteConnectionsOfShape(shapeId: string) {
    //get the lines exiting shape with id
    var linesArray = Globals.linesOUT.get(shapeId)!

    //now the shapeID is the from
    //traverse array of lines
    for (let lineId of linesArray) {
      //get the array containing the details of the certain line 
      var connectionArray = Globals.fromTo.get(lineId)!
      //get the to element
      var to = connectionArray[1]

      //now go to the other end of the line and delete the line id
      var otherEndArray = Globals.linesIN.get(to)!
      this.deleteElementFromArray(otherEndArray, lineId)

      Globals.stage.findOne('#' + lineId).destroy()
      this.deleteElementFromArray(Globals.lines, lineId)

      Globals.fromTo.delete(lineId)
    }
    Globals.linesOUT.delete(shapeId)


    //now get the lines entering the shapeID 
    var linesArray2 = Globals.linesIN.get(shapeId)!
    for (let lineId of linesArray2) {
      var connectionArray = Globals.fromTo.get(lineId)!
      var from = connectionArray[0]
      //now go to the other end and delete the line id
      var otherEndArray = Globals.linesOUT.get(from)!
      this.deleteElementFromArray(otherEndArray, lineId)
      Globals.stage.findOne('#' + lineId).destroy()
      this.deleteElementFromArray(Globals.lines, lineId)

      Globals.fromTo.delete(lineId)
    }
    Globals.linesIN.delete(shapeId)


  }



  deleteConnectionsOfaLine(lineID: string) {
    var connectionArray = Globals.fromTo.get(lineID)!
    var from = connectionArray[0]
    //now go to the other end and delete the line id
    var firstEndArray = Globals.linesOUT.get(from)!
    this.deleteElementFromArray(firstEndArray, lineID)
    var connectionArray = Globals.fromTo.get(lineID)!
    var to = connectionArray[1]
    //now go to the other end of the line and delete the line id
    var otherEndArray = Globals.linesIN.get(to)!
    this.deleteElementFromArray(otherEndArray, lineID)

    Globals.stage.findOne('#' + lineID).destroy()
    this.deleteElementFromArray(Globals.lines, lineID)

    Globals.fromTo.delete(lineID)
  }

  deleteElementFromArray(arr: string[], target: string) {
    var index = arr.indexOf(target, 0);
    if (index > -1) {
      arr.splice(index, 1);
    }
  }

  handleMessage(str: string) {
    if(str==='disconnect'){
      this.webSocketAPI._disconnect()
      this.modes=0;
      return
    }
    var update = new Update(str)
    update.execute()
  }





  
  onSim() {
    this.resetQueuesNumbers()

    this.connect();
    this.setSimulation()
    this.modes = 5;

  }

  setSimulation() {
    this.rate= ((document.getElementById("rateInput") as HTMLInputElement).value);
    if(this.rate ===null || this.rate ===''){
      alert("The default rate is 10!")
      this.rate='10'
    }

    var modifiedQ = Globals.stage.findOne('#Qi');
    (<Shape>modifiedQ.findOne('.count')).setAttr('text', this.rate);
    var mapObj = Object.fromEntries(Globals.fromTo)
    var simulationData = {
      queues: Globals.queues,
      machines: Globals.machines,
      fromTo: mapObj
    }

    var json = JSON.stringify(simulationData)
    console.log(json);
    this.httpClient.post(this.sendDataURL, json, { responseType: 'text', observe: 'response' }).subscribe((data: any) => {
      this.startSimulation(this.rate)
    })
  }



  startSimulation(startingNum:string){
    this.httpClient.post(this.startSimURL, startingNum, { responseType: 'text', observe: 'response' }).subscribe((data: any) => {})
  }



  onReplay(){
    this.resetQueuesNumbers()
    this.modes=6
    this.connect()
    var modifiedQ = Globals.stage.findOne('#Qi');
    (<Shape>modifiedQ.findOne('.count')).setAttr('text', this.rate);
    this.httpClient.post(this.replaySimURL, { responseType: 'text', observe: 'response' }).subscribe((data: any) => {
      
    })  
  }


  resetQueuesNumbers(){
    for(let q of Globals.queues){
      var modifiedQ = Globals.stage.findOne('#' + q);
      (<Shape>modifiedQ.findOne('.count')).setAttr('text', '0');
    }
  }



  onClear(){
    Globals.layer.destroy()
    Globals.layer=new Konva.Layer()
    Globals.stage.add(Globals.layer)

    Globals.linesCount=1
    Globals.rectCount=1
    Globals.circleCount=1

    Globals.firstShape=""
    Globals.secondShape=""
    Globals.deletedShape=""

    Globals.linesIN=new Map<string, string[]>()
    Globals.linesOUT=new Map<string, string[]>()
    Globals.fromTo=new Map<string, string[]>()

    Globals.lines=[]
    Globals.points=[]
    Globals.queues=[]
    Globals.machines=[]
    

    this.modes=0;
    var initials: Queue = new Queue()
    initials.initializeQueues()
  }

  onStop(){
    this.modes=0
    this.httpClient.post(this.stopSimURL, { responseType: 'text', observe: 'response' }).subscribe((data: any) => {
    })  
    this.webSocketAPI._disconnect()
    this.onClear()
  }

}
