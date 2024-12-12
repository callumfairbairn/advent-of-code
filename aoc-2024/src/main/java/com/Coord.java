package com;

public record Coord(int x, int y) {
  Coord plus(Coord other) {
    return new Coord(this.x + other.x, this.y + other.y);
  }
}
