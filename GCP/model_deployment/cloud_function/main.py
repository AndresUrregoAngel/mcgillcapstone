from googleapiclient import discovery
from googleapiclient import errors
from flask import escape



def predict_json(project, model, parameter, version):

    service = discovery.build('ml', 'v1')
    name = 'projects/{}/models/{}'.format(project, model)

    if version is not None:
        name += '/versions/{}'.format(version)

    response = service.projects().predict(
        name=name,
        body={'instances': [ parameter
#No [1,0,3,1,0,0,0,9887.436470999999,6121.641189,1.862091964,297.99059589999996,14024.88956,36377.03625,243.17949,986.7456426000001,3051.3494579999997,31835.325760000003,259.3440741,7.2406073939999995,34997.04688,7809.794457999999,3940.237472,1996.923893,1958.7290699999999,4.170176274,46217.158189999995,4486.262922999999,543273.2239999999,82393.30799,2.25833333333333,1938.15,286.26833333333303,645.774166666667]
#Yes [1,0,37,4,0,2,0,9839.403198,6040.0485530000005,1.86055129,316.1918736,14828.880869999999,35245.535130000004,254.29390899999999,986.9844007000002,2963.8355890000003,30788.85003,250.9815341,6.430442096,35121.74535,7649.1675,3795.6400700000004,1911.964706,1913.964791,4.15392739,46007.06583,4386.319056,546658.5674,83074.83562,2.4,1914.25,348.96071428571406,794.675]
        ]}
    ).execute()

    if 'error' in response:
        raise RuntimeError(response['error'])

    print(" The prediction result is {}".format(response['predictions']))

    return response['predictions']



def launch_prediction(request):

    request_json = request.get_json(silent=True)
    if request_json and 'parameter' in request_json:
        parameter = request_json['parameter']
   
    
    res = predict_json('mcgillcapstone', 'historicalrisk', parameter, version='historicalriskml')
    
    for i in res:
        if i == 0 :
            return "The zone is not expose to a residential fire in the next 6 months." \
                    "This alert will expire in 6 months or will be renewed or cancelled if future data becomes available"
        else:
            return "The zone is likely to experience a residential fire in the next 6 months." \
                    " Please alert other divisions in the City of Montreal to be prepared. " \
                    "This alert will expire in 6 months or will be renewed or cancelled if future data becomes available "
        
    #return message


#launch_prediction()
