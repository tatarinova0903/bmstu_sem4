#ifndef CONTROLLER_H
#define CONTROLLER_H

#define ROTATION_NUMBER 3
#define SCALE_NUMBER 2
#define MOVE_NUMBER 1
#define DOWNLOAD 4
#define DELETE_NUMBER 5
#define DRAW_NUMBER 6

#include"myscene.h"
#include"action.h"
#include"figure.h"


rc_type controller(myscene_t scene, int act_number, action_t act);

#endif // CONTROLLER_H
