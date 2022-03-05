#include "myscene.h"
#include "math.h"

void draw_line_scene(myscene_t scene, point_t p1, point_t p2)
{
    scene.scene->addLine(get_point_x(p1), - get_point_y(p1), get_point_x(p2), - get_point_y(p2));
}

void clear_scene(myscene_t scene)
{
    scene.scene->clear();
}

