#ifndef CABIN_H
#define CABIN_H

#include <QObject>
#include <QTimer>
#include <QDebug>
#include <iostream>
#include "doors.hpp"

class Cabin : public QObject
{
Q_OBJECT
public:
    enum Status
    {
        REACH_TARGET,
        BOARDING,
        READY_TO_MOVE,
        MOVING,
    };

    Cabin();

signals:
    void cabinReachTarget();
    void cabinReadyToMove();
    void cabinMoving();
    void cabinBoarding();


public slots:
    void slotReadyToMove();
    void slotMoving();
    void slotReadyToStop();
    void slotBoarding();

private:
    Doors doors;
    Status status = READY_TO_MOVE;
};


#endif // CABIN_H
