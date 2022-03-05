#ifndef CONTROLLER_H
#define CONTROLLER_H

#include"myscene.h"
#include"action.h"
#include"figure.h"

enum ActionType {
    ROTATE, SCALE, MOVE, DOWNLOAD, DELETE, DRAW
};


return_code controller(myscene_t scene, ActionType action, action_t act);

#endif // CONTROLLER_H
