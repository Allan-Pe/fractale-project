import { FractalProperties } from "../services/interfaces";

class History {
    public cache: Array<FractalProperties>;

    

    constructor() {
        this.cache = new Array<FractalProperties>()
    }

    takeSnapshot(): Memento {
        return new Memento(this.cache.slice());
    }

    restore(memento: Memento): void {
        this.cache = memento.cache;
    }
}

class Memento {
    public cache: Array<FractalProperties>;

    constructor(cacheSave: Array<FractalProperties>) {
        this.cache = cacheSave;
    }
}


export class HistoryHandler {
    public history: History;
    public stateHistory: Memento[];
    public currentIndex: number;
    public activeIndex: number;

    constructor() {
        this.history = new History();
        this.stateHistory = [new Memento([])];
        this.currentIndex = 1;
        this.activeIndex = this.stateHistory.length - 1;
    }

    addElementToCache(element: FractalProperties): void {
        if (this.activeIndex < this.stateHistory.length - 1) {
            this.stateHistory = this.stateHistory.slice(0, this.activeIndex + 1);
            this.history.restore(this.stateHistory[this.activeIndex]);
        }
        this.history.cache.push(element);
        this.stateHistory.push(this.history.takeSnapshot());
        this.currentIndex++;
        this.activeIndex++;
    }

    undo(): void {
        if (this.activeIndex <= 0) return;
        this.activeIndex--;
        this.currentIndex--;
        this.history.restore(this.stateHistory[this.activeIndex]);
    }

    redo(): void {
        if (this.stateHistory.length <= 0 || this.activeIndex >= this.stateHistory.length - 1) {
            console.log("Redo fail");
            return;
        }
        this.activeIndex++;
        this.currentIndex++;
        this.history.restore(this.stateHistory[this.activeIndex]);
    }

    getAll(): void {
        console.log("history:", this.history.cache);
        console.log("stateHistory:", this.stateHistory);
        console.log("currentIndex:", this.currentIndex);
        console.log("activeIndex:", this.activeIndex);
    }
}