
export class Globals{
    static stage: any
    static layer: any
    static points: number[] = []
    static queues: string[] = []
    static machines: string[] = []
    static lines: string[] = []

    //lines entering each shape
    static linesIN: Map<string, string[]> = new Map<string, string[]>()
    //lines exiting each shape
    static linesOUT: Map<string, string[]> = new Map<string, string[]>()
    //map to link line to ids of the objects it connects to reach them when deleting
    static fromTo: Map<string, string[]> = new Map<string, string[]>()

    static firstShape: string = ""
    static secondShape: string = ""
    static deletedShape: string = ""

    static circleCount: number = 1;
    static rectCount: number = 1;
    static linesCount: number = 1;


}