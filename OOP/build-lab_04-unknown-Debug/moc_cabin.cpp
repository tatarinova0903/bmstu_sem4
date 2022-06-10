/****************************************************************************
** Meta object code from reading C++ file 'cabin.hpp'
**
** Created by: The Qt Meta Object Compiler version 67 (Qt 5.12.12)
**
** WARNING! All changes made in this file will be lost!
*****************************************************************************/

#include "../lab_04/cabin.hpp"
#include <QtCore/qbytearray.h>
#include <QtCore/qmetatype.h>
#if !defined(Q_MOC_OUTPUT_REVISION)
#error "The header file 'cabin.hpp' doesn't include <QObject>."
#elif Q_MOC_OUTPUT_REVISION != 67
#error "This file was generated using the moc from 5.12.12. It"
#error "cannot be used with the include files from this version of Qt."
#error "(The moc has changed too much.)"
#endif

QT_BEGIN_MOC_NAMESPACE
QT_WARNING_PUSH
QT_WARNING_DISABLE_DEPRECATED
struct qt_meta_stringdata_Cabin_t {
    QByteArrayData data[10];
    char stringdata0[123];
};
#define QT_MOC_LITERAL(idx, ofs, len) \
    Q_STATIC_BYTE_ARRAY_DATA_HEADER_INITIALIZER_WITH_OFFSET(len, \
    qptrdiff(offsetof(qt_meta_stringdata_Cabin_t, stringdata0) + ofs \
        - idx * sizeof(QByteArrayData)) \
    )
static const qt_meta_stringdata_Cabin_t qt_meta_stringdata_Cabin = {
    {
QT_MOC_LITERAL(0, 0, 5), // "Cabin"
QT_MOC_LITERAL(1, 6, 16), // "cabinReachTarget"
QT_MOC_LITERAL(2, 23, 0), // ""
QT_MOC_LITERAL(3, 24, 16), // "cabinReadyToMove"
QT_MOC_LITERAL(4, 41, 11), // "cabinMoving"
QT_MOC_LITERAL(5, 53, 13), // "cabinBoarding"
QT_MOC_LITERAL(6, 67, 15), // "slotReadyToMove"
QT_MOC_LITERAL(7, 83, 10), // "slotMoving"
QT_MOC_LITERAL(8, 94, 15), // "slotReadyToStop"
QT_MOC_LITERAL(9, 110, 12) // "slotBoarding"

    },
    "Cabin\0cabinReachTarget\0\0cabinReadyToMove\0"
    "cabinMoving\0cabinBoarding\0slotReadyToMove\0"
    "slotMoving\0slotReadyToStop\0slotBoarding"
};
#undef QT_MOC_LITERAL

static const uint qt_meta_data_Cabin[] = {

 // content:
       8,       // revision
       0,       // classname
       0,    0, // classinfo
       8,   14, // methods
       0,    0, // properties
       0,    0, // enums/sets
       0,    0, // constructors
       0,       // flags
       4,       // signalCount

 // signals: name, argc, parameters, tag, flags
       1,    0,   54,    2, 0x06 /* Public */,
       3,    0,   55,    2, 0x06 /* Public */,
       4,    0,   56,    2, 0x06 /* Public */,
       5,    0,   57,    2, 0x06 /* Public */,

 // slots: name, argc, parameters, tag, flags
       6,    0,   58,    2, 0x0a /* Public */,
       7,    0,   59,    2, 0x0a /* Public */,
       8,    0,   60,    2, 0x0a /* Public */,
       9,    0,   61,    2, 0x0a /* Public */,

 // signals: parameters
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void,

 // slots: parameters
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void,

       0        // eod
};

void Cabin::qt_static_metacall(QObject *_o, QMetaObject::Call _c, int _id, void **_a)
{
    if (_c == QMetaObject::InvokeMetaMethod) {
        auto *_t = static_cast<Cabin *>(_o);
        Q_UNUSED(_t)
        switch (_id) {
        case 0: _t->cabinReachTarget(); break;
        case 1: _t->cabinReadyToMove(); break;
        case 2: _t->cabinMoving(); break;
        case 3: _t->cabinBoarding(); break;
        case 4: _t->slotReadyToMove(); break;
        case 5: _t->slotMoving(); break;
        case 6: _t->slotReadyToStop(); break;
        case 7: _t->slotBoarding(); break;
        default: ;
        }
    } else if (_c == QMetaObject::IndexOfMethod) {
        int *result = reinterpret_cast<int *>(_a[0]);
        {
            using _t = void (Cabin::*)();
            if (*reinterpret_cast<_t *>(_a[1]) == static_cast<_t>(&Cabin::cabinReachTarget)) {
                *result = 0;
                return;
            }
        }
        {
            using _t = void (Cabin::*)();
            if (*reinterpret_cast<_t *>(_a[1]) == static_cast<_t>(&Cabin::cabinReadyToMove)) {
                *result = 1;
                return;
            }
        }
        {
            using _t = void (Cabin::*)();
            if (*reinterpret_cast<_t *>(_a[1]) == static_cast<_t>(&Cabin::cabinMoving)) {
                *result = 2;
                return;
            }
        }
        {
            using _t = void (Cabin::*)();
            if (*reinterpret_cast<_t *>(_a[1]) == static_cast<_t>(&Cabin::cabinBoarding)) {
                *result = 3;
                return;
            }
        }
    }
    Q_UNUSED(_a);
}

QT_INIT_METAOBJECT const QMetaObject Cabin::staticMetaObject = { {
    &QObject::staticMetaObject,
    qt_meta_stringdata_Cabin.data,
    qt_meta_data_Cabin,
    qt_static_metacall,
    nullptr,
    nullptr
} };


const QMetaObject *Cabin::metaObject() const
{
    return QObject::d_ptr->metaObject ? QObject::d_ptr->dynamicMetaObject() : &staticMetaObject;
}

void *Cabin::qt_metacast(const char *_clname)
{
    if (!_clname) return nullptr;
    if (!strcmp(_clname, qt_meta_stringdata_Cabin.stringdata0))
        return static_cast<void*>(this);
    return QObject::qt_metacast(_clname);
}

int Cabin::qt_metacall(QMetaObject::Call _c, int _id, void **_a)
{
    _id = QObject::qt_metacall(_c, _id, _a);
    if (_id < 0)
        return _id;
    if (_c == QMetaObject::InvokeMetaMethod) {
        if (_id < 8)
            qt_static_metacall(this, _c, _id, _a);
        _id -= 8;
    } else if (_c == QMetaObject::RegisterMethodArgumentMetaType) {
        if (_id < 8)
            *reinterpret_cast<int*>(_a[0]) = -1;
        _id -= 8;
    }
    return _id;
}

// SIGNAL 0
void Cabin::cabinReachTarget()
{
    QMetaObject::activate(this, &staticMetaObject, 0, nullptr);
}

// SIGNAL 1
void Cabin::cabinReadyToMove()
{
    QMetaObject::activate(this, &staticMetaObject, 1, nullptr);
}

// SIGNAL 2
void Cabin::cabinMoving()
{
    QMetaObject::activate(this, &staticMetaObject, 2, nullptr);
}

// SIGNAL 3
void Cabin::cabinBoarding()
{
    QMetaObject::activate(this, &staticMetaObject, 3, nullptr);
}
QT_WARNING_POP
QT_END_MOC_NAMESPACE
