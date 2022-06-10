#ifndef CONTROLLER_H
#define CONTROLLER_H

#include <memory>
#include <vector>
#include <QObject>
#include <QDebug>
#include <iostream>
#include "mybutton.h"
#include "cabin.hpp"

class Controller : public QObject
{
Q_OBJECT
public:
    enum Status
    {
        FREE,
        ADDING_CALL,
        DEPARTURE,
        START_MOVING,
        MOVING,
        ARRIVAL,
    };

    Controller();

signals:
    void controllerFreed();
    void controllerNeedsDeparture();
    void controllerDepartured();
    void controllerReachedTarget(const int floor);

public slots:
    void slotAddingCall(const int floor);
    void slotDeparture();
    void slotStartMoving();
    void slotMoving();
    void slotArrival();
    void slotFree();

private:
    std::vector<int>::iterator getNewTarget();
    Status status = FREE;
    std::vector<int> targets;
    int currentTarget = 1;
    int currentFloor = 1;
    int direction = 0;
    QTimer floorTimer;
};

#endif // CONTROLLER_H
