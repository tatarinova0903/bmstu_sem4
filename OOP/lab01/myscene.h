#ifndef MESCENE_H
#define MESCENE_H
#include <QGraphicsScene>
#include"rc.h"
#include "points.h"

typedef struct myscene myscene_t;

struct myscene
{
    QGraphicsScene *scene;
};


rc_type draw_line_scene(myscene_t scene, point_t p1, point_t p2);
void draw_point_scene(myscene_t scene, point_t p1);
void clear_scene(myscene_t scene);




#endif // MESCENE_H

