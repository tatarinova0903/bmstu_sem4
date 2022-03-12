#ifndef CONTROLLER_H
#define CONTROLLER_H

#include "myscene.h"
#include "action.h"

enum action_type
{
    ROTATE, SCALE, MOVE, DOWNLOAD, DELETE, DRAW
};

return_code controller(myscene_t scene, action_type action, data_t act);

#endif // CONTROLLER_H
